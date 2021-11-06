---
layout: page
title: Jonathan Lee's Project Portfolio Page

---
## Project: TaskMaster2103

TaskMaster2103 is an extension to [AddressBook- Level3](https://se-education.org/addressbook-level3/)
, a CLI-based address book JavaFX application. It adds on task tracking functionalities and allowing for a hybrid
GUI and CLI usage of the application.

Below are my contributions to the project.

- **New Feature**: Added undo/redo functionality to the application (undo:
  [#70](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/70),
  redo: [#100](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/100))

    - What it does: allows the user to navigate back and forth through their commands executed, allowing them to be undone or redone.

    - Justification: every application that has command capabilities that alter the state of the app should have an undo
    or redo functionality to rectify errors that could have been made. This speeds up the overall usage of the app.

    - Highlights: the feature can be accessed through CLI or simply using familiar shortcuts such as `Ctrl/CMD + Z` for
    **undo**, and `Ctrl/CMD + Shift + Z` or `Ctrl/CMD + Y` for **redo**.

- **New Feature**: Added command history to the application [#79](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/79)

    - What it does: allows the user to toggle through their previously executed commands.

    - Justification: to align with a true CLI interface, this function is built innately in most terminals and we wanted
    to give the same feel to the CLI users of the application. Users who chose CLI are likely to be more comfortable with
      terminal environments and it only feels right to have this feature in.

    - Highlights: Pressing up cycles through previous commands, and pressing down cycles through the preceeding commands.
    The feature also keeps track of whatever one has currently typed. Pressing up or down after typing something that
      has not been executed stores the typed command down, and can be accessed simply by pressing down to the very last
      command.

- **Code contributed**: [Reposense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=yeppog&tabRepo=AY2122S1-CS2103-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)]

- **Enhancements to existing features**: Improved the preliminary design of the help window.

    - What it does: Revamp the help window to contain information on the commands that can be viewed without the User Guide.

    - Justification: The application is intended to be used without an internet connection, and users may not be able to
    access the hosted User Guide. The help window should be able to display command information without an internet
      connection.

    - Highlights: also added a hyperlink to the hosted User Guide that opens the browser, to prevent unneccesary copy
    pasting of the guide link.

- **Testing**: Added test cases for respective commands or features that were added by myself.

- **Documentation**:

    - Add documentation for `undo`, `redo` and **Command History** in the User Guide.

    - Add documentation for `undo`, `redo` and **Command History** in the Developer Guide, together with
    Activity Diagrams.

- **Community**:

    - PRs reviewed (non-trivial) [#86](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/86), [#188](https://github.com/AY2122S1-CS2103-F09-2/tp/pull/188)



## Technical skills

Programming competencies: Java, Python, C, Javascript/Typescript, Golang
Technologies: ReactJS, ReactTS, Redux, Angular 2, MongoDB, Postgressql, SQL, Docker, Jest

## Work Experience

### Software Engineering Intern, Qavar Pte Lt


- Handled a full stack project in Golang, Angular and Docker
- Used Angular to create front-end site from given wireframes.
- Wrote unit test code in Jest.

## Projects and Competitions

### CP2106 Independent Software Development Project (Orbital)

- Created a social media platform for rock climbers to share solutions to climbing routes.
- Wrote a custom backend system using NodeJs, Typescript, MongoDB.
- Used React Typescript to handle the frontend of the website.

## Additional Information

- Fluent in English, Chinese (spoken and written)
- Basic competency in Japanese
