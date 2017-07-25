Meta:
@author Fabien
@theme actions

Narrative:
I want to apply an action
In order to make a preparation

GivenStories: org/talend/dataprep/qa/dataset/DataSetStory.story

Scenario: upper case action
When I set column name to upper case
Then change to upper case step appears in recipe for name
