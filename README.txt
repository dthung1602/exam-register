hung ng:
    assistant
        module
        user
tada:
    assistant
        semester
        exam

vth:
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

view
    semester
        / {gp}
    modules
        / {gp}
        [id] {gp}
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
        participants/[id] {gp}
    user
        / {gp}
        [id] {gp}
        add {gp}
        edit {gpp}
        delete {p}

student
    sign {gp}
    register-exam {p}
    deregister-exam {p}





