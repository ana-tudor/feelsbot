# feelsbot

## Elevator pitch

An adaptive speech therapy chatbot for autistic users, to improve conversational fluidity and emotional expression.


## Inspiration

The roots for this project started in the conversations we’ve had with friends about family members from all points in the autistic spectrum, and the hardship they can face from day to day.  If these special-needs individuals are able to speak in full sentences, oftentimes those sentences don't make sense to the average person. If they're non-verbal, usually the best options of communicating with them consist of assistive apps that give them text-based forms of easy communication, and even today such assistive technology has not been optimized in functionality or user-friendliness due to a general lack of funding. 

Many low-functioning individuals, besides having difficulty being accepted by the people around them, need constant and concentrated attention so that their communication capabilities keep improving as they grow. Our chatbot would ideally be a constant companion, recognizing what the best ways are to encourage clear communication, and train users them to be able to pick up phrases that allow them to function more easily in daily life and society. The end goal would be for the chatbot to adapt to their responses and individualize their experience in learning how to converse, ultimately building their ability to communicate with the people around them.

Families often struggle to provide for their special-needs family members at the same time as they have tend to their professional and personal lives. Our application would not only assist the target audience in improving their social functionality, it would provide support to their family members by automating a learning process which otherwise would be emotionally taxing and time-consuming.

## What it does

Adaptively leads users through training exercises based on Adaptive Behavior Analysis methods, and Discrete Trial Training models of autism therapy, strengthening their communication capabilities and social skills.

Responds in real-time to emotional states and employs mitigating responses in the case of anxiety or anger tantrums.

Employs machine learning in the form of natural language processing, allowing for quick recognition of user speech and reconciling sentiment analysis from two sources - speech and facial recognition through video feed.

Engages and entertains the user with a friendly UI :)

## How we built it

We started by researching current successful and effective professional speech therapy methods for autistic patients, deciding to use DTT because of its methodical, repetitive, and solution-oriented nature.

We then investigated natural language processing API options, deciding on Google Cloud platform services and IBM Watson. We proceeded to design a project structure and decided on the environment for ease of integration of all parts.
We worked hard to troubleshoot speech recognition, video streaming, and sentiment analysis, bringing it all together with custom-written curriculum programs. And finally, we designed a cute, engaging and spirited UI. None of this could have been done without excellent teamwork, through delegation of distinct sections to team members letting all the parts combine smoothly.


## Challenges we ran into

We had some initial difficulties choosing the correct project environment/setup in order to most easily use APIs, especially because we were looking into so many different kinds of functionality. We also found it difficult throughout to be quickly creating a user interface to complement the backend.

Of course, we had our fair share of troubleshooting existing errors or lack of clarity in the API documentation, and later debugging our separate sections once it came to code integration and backend testing. Finally, we are still seeing some inaccuracies detecting speech even with advanced Speech to Text implementation through IBM’s Watson API.

## Accomplishments that we're proud of
We are most proud of successfully employing machine learning tools and integrating them into the basic chatbot curriculum. We also managed to hit our biggest goals in terms of backend functionality, natural language processing, real time conversation integration, and parallel running of multiple processes. And in spite of our limited experience, we are very happy to have created and mostly completed a beautiful, professional project.

## What we learned

We have especially gained insight into the powerful applications of machine learning and natural language processing oriented towards human interactions. We’ve learned so much about machine learning through the accessibility of Google Cloud Platform. Interfacing between multiple languages. We have also gained extensive experience in project breakdown and the complicated process of efficiently creating a finished product through delegation.

## What's next for FeelsBot

We plan on ideally writing far more robust sets of curriculum based on ABA and DTT with the help of professional consultants, with long-term improvement goals chosen for each user by medical and personal caretakers. We would also hugely increase the functionality and engagement level of our user interface, implementing game-like reinforcement processes to reward or discourage behaviors more effectively. Part of this process would involve creating an interactive app across multiple platforms with the existing backend for easy distribution.

We would also like to add a Chat feature, as a training mode for simulating real-world interactions and building basic social skills in a more natural context for the user. And ultimately, we would plan on increasing its machine learning capabilities, allowing it to become far more individualized to each user it interacts with.
