export const validateName = (value) => {
    const regex = /^[a-zA-Z\s]*$/;
    return regex.test(value);
};

// Function to validate numbers
export const validateNumber = (value) => {
    const regex = /^[0-9]*$/;
    return regex.test(value);
};

// Function to validate email addresses
export const validateEmail = (value) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(value);
};