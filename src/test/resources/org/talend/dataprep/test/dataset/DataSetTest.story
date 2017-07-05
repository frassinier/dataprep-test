Meta:
@author Vince
@theme dataset

Narrative:
As a logged in dataprep user
I want to upload a dataset
In order to use it in a preparation

Scenario: upload dataset
Given Jimmy is logged in
When I create a dataset from file using actions.csv
Then dataset actions is opened
