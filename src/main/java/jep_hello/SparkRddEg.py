from pyspark.sql import *


def test_rdd_from_list():
    spark = SparkSession.builder.getOrCreate()
    ids = [1, 2, 3, 4]
    python_rdd = spark.sparkContext.parallelize(ids)
    python_rdd_type = str(type(python_rdd))
    print('python rdd reference : ' + python_rdd_type)
    row = Row("data")
    java_data_frame = python_rdd.map(row).toDF()._jdf
    java_df_type = str(type(java_data_frame))
    print('java df reference : ' + java_df_type)
    return java_data_frame


print(test_rdd_from_list())
