package UmbertoAmoroso.progettoFinalissimo.services;

import UmbertoAmoroso.progettoFinalissimo.entities.Utente;
import UmbertoAmoroso.progettoFinalissimo.exceptions.BadRequestExceptions;
import UmbertoAmoroso.progettoFinalissimo.exceptions.NotFoundException;
import UmbertoAmoroso.progettoFinalissimo.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
    }

    public void salvaUtente(Utente utente) {
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new BadRequestExceptions("L'email " + utente.getEmail() + " è già in uso!");
        }
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utenteRepository.save(utente);
    }

    public Utente trovaPerEmail(String email) {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato"));
    }

    public Utente getCurrentUtente() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato: " + username));
    }
}
