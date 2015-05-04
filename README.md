|                   |                                                                                                                       |
| -------------     | -------------                                                                                                         |
|**Name**           | Toxicity Chart Plugin                                                                                                 |
|**License**        | [GNU LGPL 3](http://www.gnu.org/licenses/lgpl.txt)                                                                    |
|**Author**         | Cezar Coca                                                                                                            |
|**Issue tracker**  | [https://github.com/cezarcoca/sonar-toxicity-chart/issues](https://github.com/cezarcoca/sonar-toxicity-chart/issues)  |

| Version       | Date          | Description                           | SonarQube     |               |               |
| ------------- | ------------- | -------------                         | ------------- | ------------- | ------------- |
|1.0            | Jan 09, 2015  | Supports both, Java and C# languages  | 4.5.1+ (LTS)  | [Release Notes](https://github.com/cezarcoca/sonar-toxicity-chart/wiki/Release-Notes) | [Download](http://central.maven.org/maven2/org/codehaus/sonar-plugins/toxicity-chart/sonar-toxicity-chart-plugin/1.0/sonar-toxicity-chart-plugin-1.0.jar)      |
|1.1            | May 03, 2015  | Add support for SonarQube 5.x         | 5.0+          | [Release Notes](https://github.com/cezarcoca/sonar-toxicity-chart/wiki/Release-Notes) | [Download](http://central.maven.org/maven2/org/codehaus/sonar-plugins/toxicity-chart/sonar-toxicity-chart-plugin/1.1/sonar-toxicity-chart-plugin-1.1.jar)      |

## Motivation ##

Why is the quality of the code important? Firstly, it is important for economic reasons. For calculating the total cost of a software application, the following formula [[Bek071]](http://www.amazon.com/Implementation-Patterns-Kent-Beck/dp/0321413091) can be applied:

    cost_total= cost_develop + cost_maintain

Where the maintenance cost is made of:

    cost_maintain = cost_understand + cost_change + cost_test + cost_deploy

Since statistically it has been noticed that the balance of the maintenance cost greatly exceeds that of the development cost (as depicted in image below), it becomes obvious that the monitoring of the quality of the code in view of diminishing the maintenance costs represents a key factor in the realization of a long-lasting/ stable software system.

![Software Life Cycle](https://4d7f3ccfc784b13605de2780f0f4bf21ee2162f6.googledrive.com/host/0B9tMA3RbZ5P_VS11Nk0yWkpNeXM/software_life_cycle_cost.jpg)

source: [[Object-oriented and Classical Software Engineering]](http://books.google.ro/books/about/Object_oriented_and_Classical_Software_E.html?id=2C7oLcRdahQC&redir_esc=y)

An obvious step to achieving cost effective maintenance includes selecting the appropriate maintenance strategy and monitor each of the 4 cost elements listed below:

![Read vs Write code](https://4d7f3ccfc784b13605de2780f0f4bf21ee2162f6.googledrive.com/host/0B9tMA3RbZ5P_VS11Nk0yWkpNeXM/understanding_code.png)

source: [[blog.codinghorror.com]](http://blog.codinghorror.com/when-understanding-means-rewriting/)

- `cost_understand` Programs are read more often than they are written (see above image). Code communicates well when a reader can understand it, modify it, or use it.
- `cost_change` Eliminating excess complexity and duplication enables those reading, using, and modifying programs to understand them more quickly and to change successfully in the future
- `cost_test` Testable code is essential to lower maintenance costs
- `cost_deploy` Continuous delivery practices help to drive down costs and reduce the risks



## Description / Features ##

This plugin is an implementation of the **Toxicity Chart** visualization technique presented by Erik Dörnenburg on his blog, [here](http://erik.doernenburg.com/2008/11/how-toxic-is-your-code/) and [here](http://erik.doernenburg.com/2013/06/toxicity-reloaded/). This technique is useful to quickly get an idea of the general quality of a large existing code base. **Toxicity Charts** stack multiple static analysis metrics for classes, methods, or components within an application, providing a combined *toxicity score* for each area of the code base. This can be extremely useful in helping managers and non-developers understand the internal quality of the code.

Toxicity Chart for **Hibernate** (Java)

![Toxicity Chart - Java](https://4d7f3ccfc784b13605de2780f0f4bf21ee2162f6.googledrive.com/host/0B9tMA3RbZ5P_VS11Nk0yWkpNeXM/toxicity_chart_screenshot.png)

Toxicity Chart for **NHibernate** (C#)

![Toxicity Chart - C#](https://4d7f3ccfc784b13605de2780f0f4bf21ee2162f6.googledrive.com/host/0B9tMA3RbZ5P_VS11Nk0yWkpNeXM/toxicity_chart_csharp.png)

In a **Toxicity Chart** each bar represents a class and the height of the bar shows the toxicity score for that class.  The score is based on a set of rules rules (see [Usage & Installation](#usageAndInstallation) section) and different colours are used to represent each one. This makes it possible to easily spot not only how toxic a code base is, but also how the problems are distributed and what the preponderant *code smell* is. The classes that score zero points are left off the chart. The metrics are computed using the threshold value set for corresponding rule from **SonarQube / C# SonarQube** repositories.

![Rules configuration](https://4d7f3ccfc784b13605de2780f0f4bf21ee2162f6.googledrive.com/host/0B9tMA3RbZ5P_VS11Nk0yWkpNeXM/activate_rule.png)

For example if the Method Length metric has a threshold of **30** and a class comprises three methods, one that is **25** lines, one that is **45** lines and another that is **60** lines long, then the score is calculated to be proportional to the length of the method in relation to the threshold and for the given scenario the class gets **1.5 + 2 = 3.5** points.

## Axes of quality ##

All the metrics are computed using the approach presented above.

**File Length**

Total of points accumulated due to the violation of *File Length* rule. The possible associated technical debts are: Understandability, Maintainability and Single Responsibility Principle violation.

**Method Length**

Total of points accumulated due to the violation of *Method Length* rule. The possible associated technical debts are: Understandability, Maintainability and Single Responsibility Principle violation.

**Cyclomatic Complexity**

Total of points accumulated due to the violation of *Cyclomatic Complexity* rule. The possible associated technical debts are: Understandability, Maintainability and Testability.

**Parameter Number**

Total of points accumulated due to the violation of *Parameter Number* rule. The possible associated technical debts are: Understandability, Maintainability and Testability.

**Boolean Expression Complexity**

Total of points accumulated due to the violation of *Boolean Expression Complexity* rule. The possible associated technical debts are: Understandability, Maintainability and Testability.

**Nested If Depth**

Total of points accumulated due to the violation of *Nested If Depth* rule. The possible associated technical debts are: Understandability, Maintainability and Testability.

**Nested Try Depth**

Total of points accumulated due to the violation of *Nested Try Depth* rule. The possible associated technical debts are: Understandability, Maintainability and Testability.

**Missing Switch Default**

Total of points accumulated due to the violation of *Missing Switch Default* rule. The possible associated technical debt is: Correctness.

**Class Fan Out Complexity**

Total of points accumulated due to the violation of *Class Fan out Complexity* rule. The possible associated technical debts are: Understandability, Maintainability, Testability and High Coupling.


**Anon Inner Length**

Total of points accumulated due to the violation of SonarQube Anon Inner Length rule. The possible associated technical debt is: Maintainability.
Average value

Total of points accumulated due violation of all above rules divided by the number of classes that score more than one point.

<a name="usageAndInstallation"></a>

## Installation ##

1. Download the plugin into the *SONARQUBE_HOME/extensions/plugins* directory
2. Restart the **SonarQube** server

## Usage ##

1. Make sure the **SonarQube / C# SonarQube Rules** listed below are enabled in your profile and that the threshold values are set appropriately.
2. Launch a new quality analysis to calculate the metrics.

The table below lists the **Squid** rules that should be enabled in order to generate the **Toxicity Chart**.

| Issue                       | SonarQube Repository rule   | C# SonarQube Repository rule                 |
| -------------               | -------------               | -------------                                |
|File Length	              | S00104                      | FileLoc (4.5.x) or S104 (5.x)                |
|Method Length	              | S138	                    | -                                            |
|Cyclomatic Complexity        | MethodCyclomaticComplexity  | FunctionComplexity (4.5.x) or S1541 (5.x)    |
|Parameter Number	          | S00107                      | S107	                                       |
|Boolean Expression Complexity| S1067	                    | S1067                                        |
|Nested If Depth	          | S134	                    | -                                            |
|Nested Try Depth	          | S1141	                    | -                                            |
|Missing Switch Default	      | SwitchLastCaseIsDefaultCheck| SwitchWithoutDefault (4.5.x) or S131 (5.x)   |
|Class Fan Out Complexity	  | S1200                       | ClassCoupling or AvoidExcessiveClassCoupling |
|Anon Inner Length	          | S1188                       | -                                            |

If the number of classes that score more than one point is greater than 125, then the numbers on the X axis will overlap each other. In order to make the chart readable in this situation the **Toxicity Chart** threshold value can be changed in *Configuration > General Settings > Toxicity Chart* panel. The classes that have the score below the threshold value are left off the chart. The threshold value is not taken into account in toxicity metrics computing.

![Toxicity Chart threshold setting](https://lh6.googleusercontent.com/-i61P0DyHLNY/UIks5uI4f4I/AAAAAAAAAjM/4O8DQOpVttA/s684/toxicity_chart_settings.png)

## Supported Languages ##

**Java** and **C#** projects



