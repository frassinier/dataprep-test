Meta:
@author Vince
@theme learning

Narrative:
In order to learn JBehave
As a tester
I want to define sample story for a service

Scenario: 1 simple addition
Given a variable x with value 2
When I add x by 2
Then result should equal 4

Scenario: 2 add a negative value
Given a variable x with value 3
When I add x by -1
Then result should equal 2