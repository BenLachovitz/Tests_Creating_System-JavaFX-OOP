# Knowledge Tracing Program for Kids

This repository contains a comprehensive and AI-driven Knowledge Tracing Program designed as a self-management tool for kids. The project was developed as part of a Seminar in Computational Learning at the university where I am a student. It aims to monitor kid achievement, predict educational performance, and provide targeted interventions to support learning success.

## Table of Contents

-   Introduction
    
-   Key Features
    
-   Technologies Used
    
-   System Architecture
    

## Introduction

The Knowledge Tracing Program is a tool designed to help kids manage and improve their learning progress by:

-   Accurately predicting performance.
    
-   Providing actionable feedback.
    
-   Delivering tailored resources.
    
-   Ensuring a user-friendly and accessible interface.
    

The system leverages machine learning and AI capabilities through Groq API with Llama for predictive analytics and resource recommendation, alongside a robust database and user interface.

## Key Features

-   **Predictive Analytics:** Supervised machine learning models to predict kid performance based on grades, quizzes, assignments, and other metrics.
    
-   **Resource Recommendation:** A recommendation engine to suggest learning materials when poor performance is predicted.
    
-   **Real-Time Progress Monitoring:** Dashboards to track achievements, visualize test performance by topic, and identify at-risk kids.
    
-   **Immediate Feedback:** Automatic alerts and personalized reports for kids at risk, with actionable steps and links to resources.
    
-   **User-Friendly Interface:** Developed with Tkinter for easy navigation across devices.
    
-   **Secure Data Storage:** MySQL database integration for secure storage of kid progress and metrics.
    

## Technologies Used

-   **Programming Language:** Python
    
-   **Machine Learning:** Groq API with Llama
    
-   **User Interface:** Tkinter
    
-   **Database:** MySQL
    
-   **Authentication:** Secure login/signup functionality
    

## System Architecture

1.  **Kid Sign-In:** A kid signs in using demo details for testing purposes.
    
2.  **Test Request:** The kid requests a test through the system.
    
3.  **Test Generation:** The system prompts Llama via the Groq API to generate a test based on the data the system has on the kid's learning progress.
    
4.  **Test Completion:** The kid receives the test, completes it, and submits their answers.
    
5.  **Answer Evaluation:** The system sends the kid's answers back to Llama, which checks the responses and returns the results.
    
6.  **Skill Metrics Update:** Llama provides new skill metrics based on the kid's test performance, which the system saves back to the database.
    
7.  **Report and Feedback:** The system updates the kid's progress dashboard and may generate tailored reports with recommendations for further study.
    

This structured workflow ensures an engaging and educational experience, allowing kids to track their learning progress and improve their skills efficiently.
