package com.example.kbmonly;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginActivityTest {

    private LoginActivity loginActivity;

    @Mock
    private EditText mockEtLogin;
    @Mock
    private EditText mockEtPassword;
    @Mock
    private FirebaseAuth mockAuth;
    @Mock
    private Task<AuthResult> mockTask;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        loginActivity = Mockito.spy(LoginActivity.class);
        loginActivity.etLogin = mockEtLogin;
        loginActivity.etPassword = mockEtPassword;
        loginActivity.mAuth = mockAuth;
    }

    @Test
    public void testLoginUser_WithValidCredentials() {
        String email = "test@example.com";
        String password = "password";

        when(mockEtLogin.getText().toString()).thenReturn(email);
        when(mockEtPassword.getText().toString()).thenReturn(password);
        when(mockAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask);
        when(mockTask.isSuccessful()).thenReturn(true);

        loginActivity.loginUser();

        verify(mockEtLogin).getText().toString();
        verify(mockEtPassword).getText().toString();
        verify(mockAuth).signInWithEmailAndPassword(email, password);
        verify(loginActivity).checkAuthState();
    }

    @Test
    public void testLoginUser_WithEmptyEmail() {
        String password = "password";

        when(mockEtLogin.getText().toString()).thenReturn("");
        when(mockEtPassword.getText().toString()).thenReturn(password);

        loginActivity.loginUser();

        verify(mockEtLogin).getText().toString();
        verify(mockEtLogin).setError("Email cannot be empty");
        verify(mockEtPassword).getText().toString();
    }

    @Test
    public void testLoginUser_WithEmptyPassword() {
        String email = "test@example.com";

        when(mockEtLogin.getText().toString()).thenReturn(email);
        when(mockEtPassword.getText().toString()).thenReturn("");

        loginActivity.loginUser();

        verify(mockEtLogin).getText().toString();
        verify(mockEtPassword).getText().toString();
        verify(mockEtPassword).setError("Password cannot be empty");
    }
}
