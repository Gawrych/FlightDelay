import os
import json
import pandas as pd
import datetime
import pandas
import math
import requests
import calendar

unixTime = datetime.datetime(1970, 1, 1)


def preprocess_predeparture_delay(df):
    return df.dropna(subset=['DLY_ATC_PRE_2']).to_dict('records')


def preprocess_airport_arrival_delay(df):
    return df.drop(columns=['APT_NAME', 'STATE_NAME', 'ATFM_VERSION', 'Pivot Label']). \
        dropna(subset=['FLT_ARR_1_DLY']).to_dict('records')


def preprocess_airport_traffic(df):
    return df.drop(columns=['APT_NAME', 'STATE_NAME', 'Pivot Label']). \
        dropna(subset=['FLT_TOT_1']).to_dict('records')


def preprocess_asma_additional_time(df):
    cleaned_json = df.drop(columns=['APT_NAME', 'MONTH_MON', 'STATE_NAME', 'TF', 'PIVOT_LABEL', 'COMMENT']). \
        dropna(subset=['TOTAL_ADD_TIME_MIN']).to_dict('records')

    for jsonObj in cleaned_json:
        year = jsonObj['YEAR']
        month = jsonObj['MONTH_NUM']
        last_day_of_month = calendar.monthrange(year, month)[1]

        flight_date_in_days = \
            (datetime.datetime(year, month, last_day_of_month) - unixTime).days
        jsonObj['FLT_DATE'] = flight_date_in_days
        jsonObj['STAGE'] = 'ASMA'

    return cleaned_json


def preprocess_taxi_in_additional_time(df):
    cleaned_json = df.drop(columns=['APT_NAME', 'MONTH_MON', 'STATE_NAME', 'TF', 'PIVOT_LABEL', 'COMMENT']). \
        dropna(subset=['TOTAL_ADD_TIME_MIN']).to_dict('records')

    for jsonObj in cleaned_json:
        year = jsonObj['YEAR']
        month = jsonObj['MONTH_NUM']
        last_day_of_month = calendar.monthrange(year, month)[1]

        flight_date_in_days = \
            (datetime.datetime(year, month, last_day_of_month) - unixTime).days
        jsonObj['FLT_DATE'] = flight_date_in_days
        jsonObj['STAGE'] = 'TAXI_IN'

    return cleaned_json


def preprocess_taxi_out_additional_time(df):
    cleaned_json = df.drop(columns=['APT_NAME', 'MONTH_MON', 'STATE_NAME', 'TF', 'PIVOT_LABEL', 'COMMENT']) \
        .to_dict('records')

    for jsonObj in cleaned_json:
        year = jsonObj['YEAR']
        month = jsonObj['MONTH_NUM']
        last_day_of_month = calendar.monthrange(year, month)[1]

        flight_date_in_days = \
            (datetime.datetime(year, month, last_day_of_month) - unixTime).days
        jsonObj['FLT_DATE'] = flight_date_in_days
        jsonObj['STAGE'] = 'TAXI_OUT'

    return cleaned_json


def replace_nan_with_null(data):
    """
    Recursively replaces NaN values with null in a dictionary or list.
    """
    if isinstance(data, dict):
        return {k: replace_nan_with_null(v) for k, v in data.items()}
    elif isinstance(data, list):
        return [replace_nan_with_null(item) for item in data]
    elif isinstance(data, float) and math.isnan(data):
        return None
    else:
        return data


preprocessors = {
    'PreDepartureDelay': preprocess_predeparture_delay,
    'AirportArrivalDelay': preprocess_airport_arrival_delay,
    'AirportTraffic': preprocess_airport_traffic,
    'ASMA_AdditionalTime': preprocess_asma_additional_time,
    'TaxiInAdditionalTime': preprocess_taxi_in_additional_time,
    'TaxiOutAdditionalTime': preprocess_taxi_out_additional_time,
}

endpoints = {
    'AirportTraffic': 'traffic',
    'TaxiOutAdditionalTime': 'departure',
    'ASMA_AdditionalTime': 'departure'
}

folder_path = '/home/broslaw/Programming/FlightDelayData/eurocontrol/toConvert/'
files = os.listdir(folder_path)

today = datetime.date.today()
years = []
months = []
secondYears = []
secondMonths = []

amountOfMonthsToCollectDataFrom = 5

for i in range(amountOfMonthsToCollectDataFrom):
    date = today - datetime.timedelta(days=(i + 1) * 31)
    if date.year != today.year:
        secondYears.append(date.year)
        secondMonths.append(date.month)
    else:
        years.append(date.year)
        months.append(date.month)

jsonResult = '['

for file_name in files:
    if not file_name.endswith('.xlsx'):
        continue

    excel_data_df = pandas.read_excel(folder_path + file_name, sheet_name='DATA')

    thisisjson = excel_data_df.to_json(orient='records')
    thisisjson_dict = json.loads(thisisjson)

    # Preprocess data based on file name
    titleWithoutExtension = file_name[:-5]
    preprocess_fn = preprocessors.get(titleWithoutExtension)
    if preprocess_fn:
        # Convert list of dictionaries to DataFrame
        df = pd.DataFrame(thisisjson_dict)

        df = df[((df['YEAR'].isin(years)) & (df['MONTH_NUM'].isin(months))) | (
                (df['YEAR'].isin(secondYears)) & (df['MONTH_NUM'].isin(secondMonths)))]

        thisisjson_dict = preprocess_fn(df)

        thisisjson_dict = replace_nan_with_null(thisisjson_dict)

        endpoint = endpoints.get(titleWithoutExtension)
        url = 'http://localhost:8080/'
        if endpoint is not None:
            url += endpoint
            response = requests.post(url, json=thisisjson_dict)

            response_dict = {'file_name': file_name, 'status_code': str(response.status_code),
                                  'request': response.request.__str__(), 'url': response.url}

            jsonResult += json.dumps(response_dict, indent=4)
            jsonResult += ',\n'

    # Write data to JSON file
    with open(os.path.join(folder_path, titleWithoutExtension + '.json'), 'w') as json_file:
        json.dump(thisisjson_dict, json_file)

print(jsonResult[:-2]+']')
