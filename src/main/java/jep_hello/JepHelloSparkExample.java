package jep_hello;

import jep.JepException;
import jep.SharedInterpreter;
import org.apache.spark.api.java.JavaRDD;

public class JepHelloSparkExample {

    private static String pythonBlock =
            "from pyspark.sql import *" + "\n" +
            "" + "\n" +
            "" + "\n" +
            "def test_rdd_from_list():" +  "\n" +
            "    spark = SparkSession.builder.getOrCreate()" + "\n" +
            "    ids = [1, 2, 3, 4]" + "\n" +
            "    python_rdd = spark.sparkContext.parallelize(ids)" + "\n" +
            "    python_rdd_type = str(type(python_rdd))" + "\n" +
            "    print('python rdd reference : ' + python_rdd_type)" + "\n" +
            "    row = Row(\"data\")" + "\n" +
            "    java_data_frame = python_rdd.map(row).toDF()._jdf" + "\n" +
            "    java_df_type = str(type(java_data_frame))" + "\n" +
            "    print('java df reference : ' + java_df_type)" + "\n" +
            "    return java_data_frame" + "\n" ;

    public static void main(String[] args) throws JepException {


        String path = System.getenv("PATH") ;

        System.out.println("printing full system path to verify, which python is used ");
        System.out.println(path);
        System.out.println("------------------------------------------------------------");
        System.out.println("\n");

        SharedInterpreter sharedInterpreter = new SharedInterpreter();

        sharedInterpreter.exec( pythonBlock );

        //This needs spark dependencies to be in compiled scope,
        //On changing scope of spark dependencies in pom.xml
        //getting exception py4j.java_gateway module not found
        //Object result = sharedInterpreter.getValue("test_rdd_from_list()", JavaRDD.class) ;

        Object result = sharedInterpreter.getValue("test_rdd_from_list()") ;
        System.out.println( result.getClass() );
    }
}