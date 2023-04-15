import json
from ExcelToJsonConverter import ExcelToJsonConverter


def preprocess_airport_arrival_delay(df):
    return df.drop(columns=['APT_NAME', 'STATE_NAME', 'ATFM_VERSION', 'Pivot Label']). \
        dropna(subset=['FLT_ARR_1_DLY']).to_dict('records')


class ArrivalDelayScript:

    def __init__(self):
        self.file_name = 'Airport_Arrival_ATFM_Delay'

    def convert_file(self):
        converter = ExcelToJsonConverter()
        df = converter.convert(self.file_name)
        json_dictionary = preprocess_airport_arrival_delay(df)
        json_dictionary = converter.replace_nan_to_null(json_dictionary)

        json_str = json.dumps(json_dictionary, indent=0)
        print(json_str)


if __name__ == "__main__":
    script = ArrivalDelayScript()
    script.convert_file()
