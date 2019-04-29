hung ng:
    assistant
        module
        user
tada:
    assistant
        semester
        exam
        session

vth:
    lecturer
    student
    view
        exam

hung duong
    the rest

---------------------------

/ {g}

auth
    login {gp}
    logout {g}
    // change-password

view
    semester
        / {gp}
        [id] {gp}
    modules
        / {gp}
        [id] {gp}
    session
        / {gp}
        [module id] {gp}
    exam
        / {gp}
        [id] {gp}

assistant
    semester
        add {gp}
        edit/[id] {gpp}
        delete/[id] {p}
    modules
        add {gp}
        edit/[id] {gpp}
        delete/[id] {p}
        participants/[id]
            / {gp}
            add {p}
            delete {p}
    exam
        add {gp}
        edit/[id] {gpp}
        delete/[id] {p}
        view-participants/[id] {gp}
    session
        add {gp}
        edit/[id] {gpp}
        delete/[id] {p}
        view-attendance/[id] {gp}
    user
        / {gp}
        [id] {gp}
        add {gp}
        edit {gpp}
        delete {p}

lecturer
    module-participants/[id] {gp}
    session-attendance/[id] {gp}
    exam-participants/[id] {gp}

student
    sign {gp}
    register-exam {gpp}
    deregister-exam {gpp}





