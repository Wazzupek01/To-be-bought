export const regexUsername = new RegExp("[A-Za-z][A-Za-z0-9_]{4,29}");
export const regexPassword = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{7,30})");
export const regexEmail = new RegExp("^(.+)@(.+)$");