<h3>Problem statement</h3>

https://github.com/moglideveloper/ReturnDataFrame

Above github link contains a simplified example with
* spark dependencies in **provided** scope
* jep dependency in **compile** scope

and it runs fine, when JepHelloSparkExample.java is executed.

Now, if we run program locally (from intellij IDE) with below statement   
where classes from spark binary (JavaRDD) is used to get an instance of JavaRDD :-

```
Object result = sharedInterpreter.getValue("test_rdd_from_list()", JavaRDD.class) ;
```

Program will terminate with NoClassDefFoundError exception    
**as spark dependencies are in provided scope**.

```
Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/spark/api/java/JavaRDD
	at jep_hello.JepHelloSparkExample.main(JepHelloSparkExample.java:42)
Caused by: java.lang.ClassNotFoundException: org.apache.spark.api.java.JavaRDD
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581)
	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:522)
	... 1 more
```


To avoid NoClassDefFoundError, if we change scope of spark dependencies   
in pom.xml **from provided to compile** to run program locally, then project fails with below exception :-

```
Exception in thread "main" jep.JepException: <class 'ModuleNotFoundError'>: No module named 'py4j.java_gateway'
	at /Users/dev/brew/Caskroom/miniconda/base/envs/ReturnDataFrame/lib/python3.9/site-packages/pyspark/java_gateway.<module>(java_gateway.py:29)
	at /Users/dev/brew/Caskroom/miniconda/base/envs/ReturnDataFrame/lib/python3.9/site-packages/pyspark/rdd.<module>(rdd.py:34)
	at /Users/dev/brew/Caskroom/miniconda/base/envs/ReturnDataFrame/lib/python3.9/site-packages/pyspark/__init__.<module>(__init__.py:53)
	at <string>.<module>(<string>:1)
	at jep.Jep.exec(Native Method)
	at jep.Jep.exec(Jep.java:478)
	at jep_hello.JepHelloSparkExample.main(JepHelloSparkExample.java:37)
```

---

<h3>Setup to reproduce problem</h3>

* Install miniconda or conda
* Install Intellij community edition

Note : As this project is maven project, which has a dependency on python files via jep
So, intellij plugin is required as it can load maven project.

---

<h3>Steps to reproduce problem</h3>

1. Clone https://github.com/moglideveloper/ReturnDataFrame


2. Go to **ReturnDataFrame directory** and execute below commands   
   to create and activate conda environment from environment.yml file :-


       conda env create --file environment.yml


<img width="1680" alt="image" src="https://user-images.githubusercontent.com/8999083/119265359-0c77c900-bc04-11eb-8ae5-f54c0bc63586.png">



Now activate **ReturnDataFrame** conda environment and verify weather it's activated :-

        
      conda activate ReturnDataFrame
      conda env list

<img width="827" alt="image" src="https://user-images.githubusercontent.com/8999083/119265431-58c30900-bc04-11eb-98f1-844a04739b92.png">


3. Now, load ReturnDataFrame maven project in intellij.


4. Once project is loaded in intellij,   
   environment created in step 2 needs to be configured in   
   run configuration of JepHelloSparkExample.java.   
   For that, open .run/JepHelloSparkExample.run.xml in editor and update value for   
   PATH, LD_LIBRARY_PATH, DYLD_LIBRARY_PATH with the conda environment path    
   created in step 2

<img width="1680" alt="image" src="https://user-images.githubusercontent.com/8999083/119265094-1c42dd80-bc03-11eb-9859-c1a2fb5d192c.png">


5. Select JepHelloSparkExample run configuration and run it.   
   Program should run fine.


6. Now change scope of spark dependencies in pom.xml to compile


7. Reload maven changes

<img width="1680" alt="image" src="https://user-images.githubusercontent.com/8999083/119265144-4c8a7c00-bc03-11eb-88e6-4ca8d13fb376.png">


8. Rerun JepHelloSparkExample. Program should fail with below exception :-

```
Exception in thread "main" jep.JepException: <class 'ModuleNotFoundError'>: No module named 'py4j.java_gateway'
	at /Users/dev/brew/Caskroom/miniconda/base/envs/ReturnDataFrame/lib/python3.9/site-packages/pyspark/java_gateway.<module>(java_gateway.py:29)
	at /Users/dev/brew/Caskroom/miniconda/base/envs/ReturnDataFrame/lib/python3.9/site-packages/pyspark/rdd.<module>(rdd.py:34)
	at /Users/dev/brew/Caskroom/miniconda/base/envs/ReturnDataFrame/lib/python3.9/site-packages/pyspark/__init__.<module>(__init__.py:53)
	at <string>.<module>(<string>:1)
	at jep.Jep.exec(Native Method)
	at jep.Jep.exec(Jep.java:478)
	at jep_hello.JepHelloSparkExample.main(JepHelloSparkExample.java:37)
``` 

<b>Kindly suggest how to include py4j.java_gateway, with spark dependencies in comple scope.</b>

---

<h3>Functional requirement</h3>

To get a reference of JavaRdd from python using jep library.
