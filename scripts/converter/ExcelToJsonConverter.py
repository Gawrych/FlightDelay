import math
import json
import pandas as pd
import datetime
import pandas


class ExcelToJsonConverter:

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
            file_path = self.folder_path + file_name + '.xlsx'
            excel_data_df = pandas.read_excel(file_path, sheet_name='DATA')

            json_str = excel_data_df.to_json(orient='records')
            json_dictionary = json.loads(json_str)
            df = pd.DataFrame(json_dictionary)

            df = df[((df['YEAR'].isin(self.years)) & (df['MONTH_NUM'].isin(self.months))) | (
                    (df['YEAR'].isin(self.secondYears)) & (df['MONTH_NUM'].isin(self.secondMonths)))]

            return df
        except FileNotFoundError:
            print('No such file or directory: "' + file_name + '.xlsx"')
            exit(1)

    def calculate_dates(self):
        for i in range(self.amountOfMonthsToCollectDataFrom):
            date = self.today - datetime.timedelta(days=(i + 1) * 31)
            if date.year != self.today.year:
                self.secondYears.append(date.year)
                self.secondMonths.append(date.month)
            else:
                self.years.append(date.year)
                self.months.append(date.month)

    def __init__(self):
        self.folder_path = 'src/main/resources/data/'
        self.amountOfMonthsToCollectDataFrom = 4
        self.today = datetime.date.today()
        self.years = []
        self.months = []
        self.secondYears = []
        self.secondMonths = []
        self.calculate_dates()
