# hw6-analytics-framework-anonymous-team

# How To Start 

The framework uses nanohttpd for communication between the backend and frontend. 
You can launch the backend server by navigating to the “/backend” directory and running “mvn clean install”  to install the dependencies, then running “mvn exec:exec” in the terminal to launch the backend server. 

The frontend web-based GUI can be launched by navigating to the “/visualization” directory and running the “npm install” and “npm start” commands in the terminal. 

**Note:** If you are encountering the "node: bad option: --openssl-legacy-provider" error when trying to run the frontend by using the "npm start" command, upgrading Node should resolve it. 

# Framework Description

The overarching goal of the framework is to provide visualizations for text-based resumes. For example, the three implemented plugins construct an experience timeline, a word cloud of all the highlighting terms, and a bar chart containing the word counts. The implemented data plugins can take various file types of input resumes (PDF, JSON). The resumes need to be local files for the currently implemented plugins but additional plugins can be easily extended to receive JSONObjects or Strings through other input mediums. Note that our framework is only capable of parsing resumes with a fixed format, example formats are provided under the ”backend/src/test/resources/” directory (https://github.com/CMU-17-214/hw6-analytics-framework-anonymous-team/tree/main/backend/src/test/resources). The framework also provides a GUI layout for the visualizations to be displayed. It allows the user to input the path of their resume, upload a profile photo, give a short description of themselves and render the website based on the given information. 

# How To Use 

After starting the application, an initial web page will allow the user to specify their local file path (full path required) and the type of file they would like to input (PDF, JSON). If the file is not found or the file has an illegal format, the GUI will warn the user of the error and allow them to try again. After successfully loading the resume file, the GUI will prompt the user to optionally upload a profile photo and write a short description. Then, the web page will display the given information and graphs/diagrams generated by the visualization plugins. The web page can be traversed by using a navigation bar at the top of the page. 

## Example
Try generating a personal website with our example resume under src/test/resources!
![resume_path](https://user-images.githubusercontent.com/17442968/204689617-5cb7374c-be10-471c-8ab4-2dc4e44705d3.png)

If your resume is parsed succesfully, you will be directed to upload your profile photo and give a short introduction.
![intro_photo](https://user-images.githubusercontent.com/17442968/204689619-64c50072-6636-4ff5-9e47-896e2bdb0768.png)

Now click "Generate". You personal website is here! It features the profile photo, introduction, and graphs/diagrams generated by our visualization plugins.
![about_me](https://user-images.githubusercontent.com/17442968/204689621-0a978c06-5b9a-4178-966f-937b132db2c4.png)
![timeline](https://user-images.githubusercontent.com/17442968/204689623-b00cb490-3a8d-4a6a-854f-49516cf403bb.png)
![wordcount](https://user-images.githubusercontent.com/17442968/204689614-c7546675-5d1a-4c91-81b7-e283e4c20a16.png)






# How To Extend 

The framework can be easily extended by including additional data and visualization plugins. Both plugins should implement the specified interfaces “DataPlugin.java” (https://github.com/CMU-17-214/hw6-analytics-framework-anonymous-team/blob/main/backend/src/main/java/edu/cmu/cs/cs214/hw6/framework/DataPlugin.java) and “PersonalPagePlugin.ts” (https://github.com/CMU-17-214/hw6-analytics-framework-anonymous-team/blob/main/visualization/src/framework/PersonalPagePlugin.ts) for data and visualization plugins, respectively. 



- **Data part**

The new plugin must contain the methods defined by the interface such as “getFirstName()”, “getEmail()”, “getExperience()”, etc. Detailed descriptions for each method are commented within the DataPlugin.java interface.

The main data types that the data plugin will interact with are String and List of Strings, with the exception of the “Experience” (https://github.com/CMU-17-214/hw6-analytics-framework-anonymous-team/blob/main/backend/src/main/java/edu/cmu/cs/cs214/hw6/framework/Experience.java) class. It is a class defined to better aggregate the experience information in resumes. It stores information such as title, position, and location, as strings and converts them into a JSON object to pass onto the frontend which enables less code duplication and more readability. The class is simple, intuitive to understand, and can be found within the same folder as the “DataPlugin.java” interface. 

- **Virtualization part**

Similarly, new visualization plugins must implement the methods given in the interface. The “getContent(resume: Resume)” method is in charge of generating the JSX.Element to be displayed on the GUI webpage. 

Visualization plugins are required to interact with the Resume class. It contains all the information parsed by the data plugins (user’s first name, skills, experiences, etc.), as well as data processed by the framework (wordCount). The visualizations have access and can produce diagrams using all available information given by the Resume class. The class can be found under the “/visualization/src/framework/” folder (https://github.com/CMU-17-214/hw6-analytics-framework-anonymous-team/tree/main/visualization/src/framework). 

# Data Processing 
In our framework, we are using an open-source machine learning model provided by OpenNLP to tokenize all the input texts.  The framework will join all the text data the plugin provides and leverage the machine learning model to tokenize the text to extract the individual word. So the framework can calculate the word count map. You can find detailed information about the model we use here: https://opennlp.sourceforge.net/models-1.5/. The model data is stored locally under the directory “backend/src/main/resources” (https://github.com/CMU-17-214/hw6-analytics-framework-anonymous-team/tree/main/backend/src/main/resources).
