Meta:
@author Vince
@theme dataset

Narrative:
As a logged in dataprep user
I want to upload a dataset
In order to use it in a preparation

Scenario: upload dataset
Given Jimmy is logged in
When I create a dataset from file using /home/vincent/Desktop/nba_franchises.txt
Then dataset nba_franchises is opened
