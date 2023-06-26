import math
import json
import pandas as pd


class CsvToJsonConverter:

    def replace_nan_to_null(self, data):
        if isinstance(data, dict):
            return {k: self.replace_nan_to_null(v) for k, v in data.items()}
        elif isinstance(data, list):
            return [self.replace_nan_to_null(item) for item in data]
        elif isinstance(data, float) and math.isnan(data):
            return None
        else:
            return data

    def convert(self, file_name):
        try:
            file_path = self.folder_path + file_name + '.csv'
            csv_data_df = pd.read_csv(file_path)

            json_str = csv_data_df.to_json(orient='records')

            json_dictionary = json.loads(json_str)
            df = pd.DataFrame(json_dictionary)

            asma_file_name = 'ASMA_Additional_Time'
            taxi_in_file_name = 'Taxi-In_Additional_Time'
            taxi_out_file_name = 'Taxi-Out_Additional_Time'

            if file_name != asma_file_name and file_name != taxi_in_file_name and file_name != taxi_out_file_name:
                self.amountOfMonthsToCollectDataFrom = 5
                # Convert the 'DATE' column to datetime
                df['DATE'] = pd.to_datetime(df['DATE'])

                # Convert the datetime to timestamp (long format)
                df['DATE'] = df['DATE'].apply(lambda x: int(x.timestamp() * 1000))

            self.calculate_dates(df)

            df = df[((df['YEAR'] == self.last_year)
                     & (df['MONTH_NUM'] >= self.start_month))
                    | (df['YEAR'] > self.last_year)]

            return df

        except FileNotFoundError:
            print('No such file or directory: "' + self.folder_path + file_name + '.csv"')
            exit(1)

    def calculate_dates(self, df):
        self.last_year = df['YEAR'].max()
        self.last_month = df[df['YEAR'] == self.last_year]['MONTH_NUM'].max()

        self.start_month = self.last_month - self.amountOfMonthsToCollectDataFrom + 1
        if self.start_month <= 0:
            self.start_month += 12
            self.last_year -= 1

    def __init__(self):
        self.folder_path = '/home/broslaw/Programming/flight-delay-api/src/main/resources/data/'
        self.amountOfMonthsToCollectDataFrom = 17
        self.last_year = 0
        self.last_month = 0
        self.start_month = 0
