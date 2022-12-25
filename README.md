# MeetingScheduler

Given a file through standard input in IntelliJ, this program reads in a file containing two individual's schedules separated by a "====" string in the file. The purpose of the program is to compare the schedules and the intervals of free time described, and find times in the day where both partied have enough free time for a "meeting". The duration of this meeting is given by the user of the program.

The times indicate intervals throughout the day in which the person has free time, and are separated by a '-' character.
The times should be in military time format and also entered in ascending order. 
The file should also include an integer on the first line which should indicate the minimum duration of a meeting in minutes.
It should be noted that the duration can be any number less than 60, but after 60 minutes the duration must be in multiples of 60 in order to find the correct possible meeting times. 
ex.) 60, 120, 180.




Using an arraylist of tuples, the program uses a two-pointer method to compare the times and assuming there are times where a meeting can occur, will output these times. Otherwise, the program will output a line explaining that there were no possible meeting times found.
The searching portion of this program runs in linear time. 


View this readme in raw foirmat in order to see a valid input:

30
05:30-06:30
09:00-10:00
09:45-10:30
11:00-12:00

====

04:00-04:30
05:00-07:00
10:00-11:00
10:30-13:00
14:00-18:00
