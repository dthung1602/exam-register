hung ng:
    assistant
        semester
        module
        exam
tada:
    assistant
        session
        user
        participant

vth:
    lecturer
    student
    view
        exam

hung duong
    the rest

---------------------------

auth
    login
    logout
    // change-password

view
    semester
        /
        [id]
    modules
        /
        [id]
    session
        /
        [module id]
    exam
        /
        [id]

assistant
    semester
        addle
        [id]/edit
        delete
    modules
        add
        [id]/edit
        delete
        [id]/participants
            /
            add
            delete
    session
        add
        [id]/edit
        delete
        [id]/view-attendance
    exam
        add
        [id]/edit
        delete
        [id]/view-participants
    user
        /
        [id]
        add
        [id]/edit
        delete

lecturer
    modules
        [id]/view-participants
    session
        [id]/view-attendance
    exam
        [id]/view-participants

student
    sign
        /
    register-exam
        /
    deregister-exam
        /





