# SoundBar  
A small Java Programm to play music files.  

## Setup    
To use this programm you need to add your own soundtracks.  
Create this folder structure:    
folder/SoundBar/src/...   
folder/resources/files (Put all script files in this folder as *.txt* files)  
folder/resources/sounds (Put all your music and soundeffects in this folder as *.wav* files)  
  
To use sound files in the UI they need a name starting with "Background".  

## Useage    
### UI  
With the UI you can controll the shown tracks. 
The UI is updated every second (controlled by the *FPS* and the *updateSeq* in the *SoundBarMain*-class).   
  
### Terminal    
With the terminal you can use all implemented commands.  
Syntax:   
command soundname parameter...  
script inputVariables
  
If no command is found the programm tries to start a fitting script.

#### Terminal Commands
"window": opens a new Window  
"start": *trackname* *targetVolume default: 1* *timeToTargetVolume default: 0* starts the track  
"fade": *trackname* *targetVolime* *timeToTargetVolume* fades to the desired volume  
"stop": *trackname* stops the track  
"pause": *trackname* *delay* stops the track with an delay  
"run": *trackname* starts the track  
"res": *trackname* sets the tracks frame to 0 (starts from the beginning)  
"repeat": *trackname* *pause* starts and repeats the track after the pause  
"spfade": *trackname* *targetVolume* *timeToPause* fades the track to a pause  
"stopfade": *trackname* *timeToStop* fades the track to a stop  
"setRePa": *trackname* *time* sets the repeat pause time  
"addRePa": *trackname* *time* adds to the repeat pause time  
"setDel": *trackname* *time* sets an delay for the track  
"addDel": *trackname* *time* adds to the delay of an track  
"autoRep": *trackname* *true/false* sets if an track should repeat itself  
"setFPS": *FPS* sets the FPS of this programm  
"setUpdateSeq": *updateSequence* sets how often the UI is refreshed  
"replaceCommand": *toReplace* *replacement* with this you can change the command names  
"setSrcPath": *path* sets the sourcePath for this programm. Use "restart" afterwards  
"setStandardFadeTime": *time* sets the standartly used time to fade a track  
"restart": restarts the programm  
"end": exits the programm  

### Scripts
Scripts are .txt files placed in the *resources/files* folder they contain instructions.  
These scripts can be started by typing theyre name into the terminal.  
Scripts are executed line by line with the speed of the FPS.  
A line of code is only executed if the starting number is equal with the tickes it has been alive for.  
A script has:  
A name to find it  
A set of instructions it has to execute  
An counter how long it has been alive for (ticks)  
A state if it is running or not  
An integer variable to store values  
An String array to store input varaibles (given in the terminal)  
  
Syntax:  
tickToExecute # scriptCommand parameters
tickToExecute terminalCommand parameters
  
#### Script Commands  
"save": saves an Integer  
"add": add a number to its stored Integer  
"sub": subtract a number from its stored Integer  
"mult": multiply its stored Integer  
"div": divide its stored Integer  
"clear": set the stored Integer to 0  
"if": if the stored Integer equades to the parameter the following parameters are treated as an command   
"smaler": if the given number is smaler than the stored Integer the following parameters are treated as an command  
"greater": if the given number is greater than the stored Integer the following parameters are treated as an command  
"bitAnd": bitwise & combination with the stored Integer and the parameter  
"bitOr": bitwise | combination with the stored Integer and the parameter  
"bitXOr": bitwise ^ combination with the stored Integer and the parameter  
"bitComp": bitwise ~ compliment of the stored Integer  
"bitShift<": bitwise < shifting the stored Integer with the parameter  
"bitShift>": bitwise > shifting the stored Integer with the parameter  
"bitShift>>>": bitwise >>> shifting the stored Integer with the parameter  
"settick": sets the tick value  
"addtick": adds to the tick value  
"subtick": subtracts from the tick value  
"sysout": prints the parameter  