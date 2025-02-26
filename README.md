
# Task Tracker CLI
Task Tracker is a productivity tool designed to help individuals manage tasks efficiently.

## **Features**
  - Create, edit, delete, and mark tasks as completed.
  - Simple **command-line usage**

## Installation

### **1. Clone the repository**
```bash
git clone https://github.com/CincoFolha/task-tracker.git
cd task-tracker
```

### **2. Build the project**
Using Gradle Wrapper:
```bash
./gradlew build
```
Or on Windows:
```bash
gradlew.bat build
```

### **3. Run the application**
```bash
./gradlew run
```


## Usage
```bash
# Adding a new task
./gradlew run --args="add 'Buy groceries'"
# Output: Task added successfully (ID: 1)

# Updating and deleting tasks
./gradlew run --args="update 1 'Buy groceries and cook dinner'"
./gradlew run --args="delete 1"

# Marking a task as in progress or done
./gradlew run --args="mark-in-progress 1"
./gradlew run --args="mark-done 1"

# Linting all tasks
./gradlew run --args="list"

# Listing tasks by status
./gradlew run --args="list done"
./gradlew run --args="list todo"
./gradlew run --args="list in-progress"
```

## **Technologies Used**
- **Java**
- **Gradle**

## URL
https://roadmap.sh/projects/task-tracker
