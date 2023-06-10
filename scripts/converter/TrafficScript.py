import json
from CsvToJsonConverter import CsvToJsonConverter


def preprocess_airport_traffic(df):
    return df.drop(columns=['APT_NAME', 'STATE_NAME', 'Pivot Label']). \
        dropna(subset=['FLT_TOT_1']).to_dict('records')


class TrafficScript:
    def __init__(self):
        self.file_name = 'Airport_Traffic'

    def convert_file(self):
        converter = CsvToJsonConverter()
        df = converter.convert(self.file_name)
        json_dictionary = preprocess_airport_traffic(df)
        json_dictionary = converter.replace_nan_to_null(json_dictionary)

        json_str = json.dumps(json_dictionary, indent=0)

        print(json_str)


if __name__ == "__main__":
    script = TrafficScript()
    script.convert_file()
