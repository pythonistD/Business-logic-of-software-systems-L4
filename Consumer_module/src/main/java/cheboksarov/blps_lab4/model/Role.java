package cheboksarov.blps_lab4.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cheboksarov.blps_lab4.model.Permissions.*;


@RequiredArgsConstructor
public enum Role {
    USER(
       Set.of(
            MAKE_BET,
            GET_MY_BETS,
            TOP_BALANCE,
            GET_MY_BALANCE,
            GET_ALL_BETS,
            GET_MATCHES,
            GET_COEFFICIENT,
            GET_STATISTIC
       )
    ),
    ADMIN(
        Set.of(
                GET_USERS,
                MAKE_BET,
                GET_MY_BETS,
                TOP_BALANCE,
                GET_MY_BALANCE,
                GET_ALL_BETS,
                // Coefficient
                GET_COEFFICIENT,
                CREATE_COEFFICIENT,
                DELETE_COEFFICIENT,
                UPDATE_COEFFICIENT,
                // Statistic
                CREATE_STATISTIC,
                DELETE_STATISTIC,
                UPDATE_STATISTIC,
                GET_STATISTIC,
                // Match management
                GET_MATCHES,
                CREATE_MATCH,
                DELETE_MATCH,
                UPDATE_MATCH
        )
    ),
    BETMASTER(
        Set.of(
            GET_MATCHES,
            GET_COEFFICIENT,
            CREATE_COEFFICIENT,
            DELETE_COEFFICIENT,
            UPDATE_COEFFICIENT
        )
    ),
    STATMASTER(
            Set.of(
                    GET_MATCHES,
                    UPDATE_MATCH,
                    CREATE_MATCH,
                    DELETE_MATCH,
                    GET_STATISTIC,
                    CREATE_STATISTIC,
                    DELETE_STATISTIC,
                    UPDATE_STATISTIC
            )
    );

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
