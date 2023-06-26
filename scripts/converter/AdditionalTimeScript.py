import datetime
import json
from CsvToJsonConverter import CsvToJsonConverter


def departure_additional_time_completion(json_to_be_completed, stage):
    unix_time = datetime.datetime(1970, 1, 1)
    for jsonObj in json_to_be_completed:
        year = jsonObj['YEAR']
        month = jsonObj['MONTH_NUM']
        jsonObj['DATE'] = (datetime.datetime(year, month, 1) - unix_time).total_seconds() * 1000
        jsonObj['STAGE'] = stage

    return json_to_be_completed


def preprocess_asma_additional_time(df):
    cleaned_json = df.drop(columns=['APT_NAME', 'MONTH_MON', 'STATE_NAME', 'TF', 'PIVOT_LABEL', 'COMMENT']). \
        dropna(subset=['TOTAL_ADD_TIME_MIN']).to_dict('records')

    return departure_additional_time_completion(cleaned_json, 'ASMA')


def preprocess_taxi_in_additional_time(df):
    cleaned_json = df.drop(columns=['APT_NAME', 'MONTH_MON', 'STATE_NAME', 'TF', 'PIVOT_LABEL', 'COMMENT']). \
        dropna(subset=['TOTAL_ADD_TIME_MIN']).to_dict('records')

    return departure_additional_time_completion(cleaned_json, 'TAXI_IN')


def preprocess_taxi_out_additional_time(df):
    cleaned_json = df.drop(columns=['APT_NAME', 'MONTH_MON', 'STATE_NAME', 'TF', 'PIVOT_LABEL', 'COMMENT']). \
        dropna(subset=['TOTAL_ADD_TIME_MIN']).to_dict('records')

    return departure_additional_time_completion(cleaned_json, 'TAXI_OUT')


class DepartureAdditionalTimeScript:

    def __init__(self):
        self.asma_file_name = 'ASMA_Additional_Time'
        self.taxi_in_file_name = 'Taxi-In_Additional_Time'
        self.taxi_out_file_name = 'Taxi-Out_Additional_Time'

    def convert_file(self):
        converter = CsvToJsonConverter()
        asma_df = converter.convert(self.asma_file_name)
        taxi_in_df = converter.convert(self.taxi_in_file_name)
        taxi_out_df = converter.convert(self.taxi_out_file_name)

        json_dictionary = []

        if asma_df is not None:
            json_dictionary.extend(preprocess_asma_additional_time(asma_df))

        if taxi_in_df is not None:
            json_dictionary.extend(preprocess_taxi_in_additional_time(taxi_in_df))

        if taxi_out_df is not None:
            json_dictionary.extend(preprocess_taxi_out_additional_time(taxi_out_df))

        json_dictionary = converter.replace_nan_to_null(json_dictionary)

        json_str = json.dumps(json_dictionary, indent=0)

        print(json_str)


if __name__ == "__main__":
    script = DepartureAdditionalTimeScript()
    script.convert_file()
