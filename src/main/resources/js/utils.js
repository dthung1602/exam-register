function displayError(response, status, error) {
    switch (error) {
        case 'Bad Request':
            alert(response);
            break;
        case 'Internal Server Error':
            alert("Internal Server Error\nPlease try again later");
            break;
        default :
            alert("Unknown error\nPlease try again later");
    }
}


