import pandas as pd
import os

# CSV 파일들이 있는 디렉토리
directory_path = '/Users/onlyoneprivate/Documents/소상공인시장진흥공단_상가(상권)정보'

# 결과를 저장할 빈 DataFrame 생성
result_df = pd.DataFrame()

# 디렉토리 내의 모든 파일 순회
for file in os.listdir(directory_path):
    # CSV 파일인지 확인
    if file.endswith('.csv'):
        file_path = os.path.join(directory_path, file)

        # CSV 파일 읽기
        df = pd.read_csv(file_path)

        # '상호명' 열에서 '이디야'를 포함하는 행 필터링
        filtered_df = df[df['상호명'].str.contains('이디야', na=False)]

        # 필요한 열 추출
        extracted_data = filtered_df[['상호명', '도로명', '위도', '경도']]

        # 결과 DataFrame에 추가
        result_df = pd.concat([result_df, extracted_data], ignore_index=True)

# 결과를 새로운 CSV 파일로 저장
output_file_path = '/Users/onlyoneprivate/Documents/ediya.csv'
result_df.to_csv(output_file_path, index=False, encoding='utf-8-sig')

