package com.app.learningcards.services.security;

import com.app.learningcards.request.config.AuthenticationRequest;
import com.app.learningcards.request.config.AuthenticationResponse;
import com.app.learningcards.request.config.RegisterRequest;
import com.app.learningcards.models.Role;
import com.app.learningcards.models.User;
import com.app.learningcards.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request)
    {
        final String login = request.getLogin();
        final String password = request.getPassword();
        final String email = request.getEmail();

        User user = new User(request.getLogin(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                Role.USER);

        if(!loginCorrect(login) || !passwordCorrect(password) || !emailCorrect(email))
            return emptyToken();

        if(userService.loginOccupied(user) || userService.emailOccupied(user))
            return  emptyToken();

        userService.registerUser(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        User user = userService.getUser(request.getLogin());
        if(user == null)
            //TO DO
          ;

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    private AuthenticationResponse emptyToken()
    {
        return  AuthenticationResponse.builder().token("").build();
    }
    
    



    public static boolean passwordCorrect(String password)
    {
        int len = password.length();
        if(len < 8 || len > 16)
            return false;

        if(!containsSpecialChar(password))
            return false;

        //contains big letter
        if(!Pattern.matches("^.*[A-Z].*$", password))
            return false;

        //contains small letter
        if(!Pattern.matches("^.*[a-z].*$", password))
            return false;

        return true;
    }
    public static boolean loginCorrect(String login)
    {
        Pattern pattern = Pattern.compile("^[A-Z0-9]{5,16}$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(login).matches();
    }


    public boolean emailCorrect(String email)
    {
        return testUsingRFC5322Regex(email);
    }

    private boolean testUsingRFC5322Regex(String email)
    {
        return Pattern.compile( "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches();
    }

    private static boolean containsSpecialChar(String password)
    {
        List<Character> specialChar = List.of('!','@','#','$','%','^','&','*');

        for(int i = 0 ; i < password.length(); i++)
            if(specialChar.contains(password.charAt(i)))
                return true;

        return false;
    }
}
