Meta:
@author Vince
@theme dataset

Narrative:
I want to upload a dataset
In order to use it in a preparation

Scenario: upload dataset
When I create a dataset from file using actions.csv
Then dataset actions is opened
