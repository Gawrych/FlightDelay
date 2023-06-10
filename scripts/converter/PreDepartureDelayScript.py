import json
from CsvToJsonConverter import CsvToJsonConverter


def preprocess_predeparture_delay(df):
    return df[df['FLT_DEP_1'] != 0].dropna(subset=['DLY_ATC_PRE_2']).to_dict('records')


class PreDepartureDelayScript:
    def __init__(self):
        self.file_name = 'ATC_Pre-Departure_Delay'

    def convert_file(self):
        converter = CsvToJsonConverter()
        df = converter.convert(self.file_name)
        json_dictionary = preprocess_predeparture_delay(df)
        json_dictionary = converter.replace_nan_to_null(json_dictionary)

        json_str = json.dumps(json_dictionary, indent=0)

        print(json_str)


if __name__ == "__main__":
    script = PreDepartureDelayScript()
    script.convert_file()
