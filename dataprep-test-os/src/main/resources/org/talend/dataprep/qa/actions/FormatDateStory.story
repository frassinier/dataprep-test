Meta:
@author Fabien
@theme actions

Narrative:
I want to change date format
In order to complete my preparation

GivenStories: org/talend/dataprep/qa/dataset/DataSetStory.story

Scenario: format date action
Given column created having MM/dd/yyyy date pattern
When I change created date pattern to dd/MM/yyyy
Then created dates pattern should match dd/MM/yyyy