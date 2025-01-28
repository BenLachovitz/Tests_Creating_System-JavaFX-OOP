
# Quiz Management System

A Java-based desktop application for creating, managing, and generating quizzes. Built using JavaFX and following the MVC (Model-View-Controller) architecture pattern.

## Features

### Question Bank Management
- Add new questions (both open-ended and multiple-choice) with their correct answers
- Delete existing questions
- Edit question content
- Manage answers for multiple-choice questions
- View all questions in the bank

### Quiz Generation
- Create quizzes manually by selecting specific questions
- Generate quizzes automatically based on specified number of questions
- Auto-save both quiz and corresponding solution files
- Clone existing quizzes with new timestamp

### Data Persistence
- All questions and their correct answers are stored in text files
- Quizzes and solutions are automatically saved as separate files
- Support for quiz duplication with unique identifiers

## Technical Stack
- Language: Java
- UI Framework: JavaFX
- Architecture: MVC (Model-View-Controller)
- Storage: Text-based file system

## Usage

### Adding Questions
1. Navigate to the Question Bank section
2. Click "Add New Question"
3. Choose question type (Open/Closed)
4. Fill in the question details
5. Provide the correct answer for the question
6. Save the question

### Creating a Quiz
#### Manual Creation
1. Go to Quiz Generation
2. Select "Create Manual Quiz"
3. Choose questions from the bank
4. The system will generate both exam and solution files

#### Automatic Generation
1. Go to Quiz Generation
2. Select "Generate Automatic Quiz"
3. Specify the number of questions
4. System will randomly select questions
5. Both exam and solution files are automatically generated

### File Naming Convention
- Quizzes are saved with format: `exam_YYYY_MM_DD.txt`
- Solution files are saved with format: `solution_YYYY_MM_DD.txt`
- When cloning a quiz:
  - If created on a different day: New timestamp is used
  - If created on the same day: Original timestamp with "-copy" suffix is used

## Academic Context
This project was developed as part of a first-year, second-semester Object-Oriented Programming course, demonstrating practical implementation of:
- OOP principles
- MVC architecture
- GUI development
- File I/O operations
- Data structure management

## Contributing
This is an academic project and was completed as part of coursework. However, suggestions and improvements are welcome.
