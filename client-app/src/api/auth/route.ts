import {API_SIGN_UP_USER, API_SIGN_IN_USER, API_EDIT_ADMIN, API_GET_MYSELF_USER, API_SING_IN_MANAGER, API_GET_MYSELF_MANAGER, API_SING_IN_ADMIN, API_GET_MYSELF_ADMIN, API_SIGN_UP_MANAGER, API_EDIT_USER, API_CHANGE_PASSWORD_USER, API_EDIT_MANAGER, API_CHANGE_PASSWORD_MANAGER, API_GET_ALL_USERS, API_BAN_USER, API_UNBAN_USER, API_GET_ALL_MANAGERS, API_BAN_MANAGER, API_UNBAN_MANAGER} from "@/api/constants";


export const sendLoginRequestUser = async (username: string, password: string) => {
    return (await fetch(API_SIGN_IN_USER, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
        body: JSON.stringify({username, password}),
    }));
};

export const sendRegisterRequestUser = async (username: string, password: string,
    email: string, dateBirth: Date, name: string, lastName: string) => {
    return (await fetch(API_SIGN_UP_USER, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
        body: JSON.stringify({username, password, email, dateBirth, name, lastName}),
    }));
};

export const sendLoginRequestManager = async (username: string, password: string) => {
    return (await fetch(API_SING_IN_MANAGER, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
        body: JSON.stringify({username, password}),
    }));
};

export const sendRegisterRequestManager = async (username: string, password: string,
    email: string, dateBirth: Date, name: string, lastName: string, sportsHall: string, dateEmployment: Date) => {
    return (await fetch(API_SIGN_UP_MANAGER, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
        body: JSON.stringify({username, password, email, dateBirth, name, lastName, sportsHall, dateEmployment}),
    }));
};

export const sendLoginRequestAdmin = async (username: string, password: string) => {
    return (await fetch(API_SING_IN_ADMIN, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json'},
        body: JSON.stringify({username, password}),
    }));
};

export const sendEditUserRequest = async (email: string, dateBirth: Date, name: string, lastName: string, token: string) => {
    return (await fetch(API_EDIT_USER, {
        method: 'PUT',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
        body: JSON.stringify({email, dateBirth, name, lastName}),
    }));
};

export const sendEditAdminRequest = async (email: string, dateBirth: Date, name: string, lastName: string, token: string) => {
    return (await fetch(API_EDIT_ADMIN, {
        method: 'PUT',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
        body: JSON.stringify({email, dateBirth, name, lastName}),
    }));
};

export const sendEditManagerRequest = async (email: string, dateBirth: Date, name: string, lastName: string, sportsHall: string, dateEmployment:Date, token: string) => {
    return (await fetch(API_EDIT_MANAGER, {
        method: 'PUT',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
        body: JSON.stringify({email, dateBirth, name, lastName, sportsHall, dateEmployment}),
    }));
};

export const sendGetMyselfUser = (token: string) =>
    fetch(API_GET_MYSELF_USER, {
        method: 'GET',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});

export const sendGetMyselfManager = (token: string) =>
    fetch(API_GET_MYSELF_MANAGER, {
        method: 'GET',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});

export const sendGetMyselfAdmin = (token: string) =>
    fetch(API_GET_MYSELF_ADMIN, {
        method: 'GET',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});

export const changePasswordUser = (oldPassword: string, newPassword: string, token: string) =>
    fetch(API_CHANGE_PASSWORD_USER, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
        body: JSON.stringify({oldPassword, newPassword}),
});

export const getAllUsers = (token: string, page: number, size: number) =>
    fetch(API_GET_ALL_USERS+"?page="+page+"&size="+size, {
        method: 'GET',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});

export const getAllManagers = (token: string, page: number, size: number) =>
    fetch(API_GET_ALL_MANAGERS+"?page="+page+"&size="+size, {
        method: 'GET',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});

export const changePasswordManager = (oldPassword: string, newPassword: string, token: string) =>
    fetch(API_CHANGE_PASSWORD_MANAGER, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
        body: JSON.stringify({oldPassword, newPassword}),
});

export const banUser = (token: string, id: number) =>
    fetch(API_BAN_USER+"/"+id, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});

export const banManager = (token: string, id: number) =>
    fetch(API_BAN_MANAGER+"/"+id, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});

export const unbanManager = (token: string, id: number) =>
    fetch(API_UNBAN_MANAGER+"/"+id, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});

export const unbanUser = (token: string, id: number) =>
    fetch(API_UNBAN_USER+"/"+id, {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type': 'application/json', "Authorization": "Bearer " + token},
});