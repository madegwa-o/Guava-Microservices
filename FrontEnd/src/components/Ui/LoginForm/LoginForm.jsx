import React, { useState } from 'react';
import styles from './LoginForm.module.css';

function LoginForm() {
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent the default form submission behavior
        const { username, password } = formData;
        console.log('Username:', username);
        console.log('Password:', password);
        // Add your logic here to handle login, e.g., make a request to your server
    };

    return (
        <div className={styles.container}>
            <h1>Guava Login</h1>
            <div className={styles.LoginForm}>
                <form onSubmit={handleSubmit}>
                    <div className={styles.username}>
                        <input
                            type="text"
                            name="username"
                            id="username"
                            placeholder="username"
                            value={formData.username}
                            onChange={handleChange}
                        />
                    </div>
                    <div className={styles.password}>
                        <input
                            type="password"
                            name="password"
                            id="password"
                            placeholder="password"
                            value={formData.password}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <button type="submit">Login</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default LoginForm;
