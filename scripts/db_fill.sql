insert into statistics(stat_id, corner_kicks, penalties, r_cards, score, shots_on_target, y_cards) values
(1, 1, 1, 1, 1, 10, 1),
(2, 1, 1, 1, 1, 10, 1),
(3, 1, 1, 1, 1, 10, 1),
(4, 1, 1, 1, 1, 10, 1);
insert into statistics(stat_id, corner_kicks, penalties, r_cards, score, shots_on_target, y_cards) values
(5, 1, 1, 1, 1, 10, 1),
(6, 1, 1, 1, 1, 10, 1),
(7, 1, 1, 1, 1, 10, 1),
(8, 1, 1, 1, 1, 10, 1);
insert into coefficient(coeff_id, guests_wins, hosts_wins, total_one, total_three, total_two) values
(1, 1.0, 2.0, 2.1, 5.5, 4.3),
(2, 1.0, 2.0, 2.1, 5.5, 4.3),
(3, 1.0, 2.0, 2.1, 5.5, 4.3),
(4, 1.0, 2.0, 2.1, 5.5, 4.3);

insert into match(match_id, status, fk_coeff_id, fk_statistic_guests, fk_statistic_hosts, guests, hosts, time_start, time_end) values
(1, 0, 1, 1, 2, 'team1', 'team2', now(), now() + interval '90 minutes'),
(2, 0, 2, 3, 4, 'Black', 'White', now(), now() + interval '90 minutes');
insert into match(match_id, status, fk_coeff_id, fk_statistic_guests, fk_statistic_hosts, guests, hosts, time_start, time_end) values
(3, 0, 3, 5, 6, 'VT', 'KT', now(), now() + interval '1 minutes'),
select * from "match";
insert into match(match_id, status, fk_coeff_id, fk_statistic_guests, fk_statistic_hosts, guests, hosts, time_start, time_end) values
(4, 0, 4, 7, 8, 'VT', 'KT', now(), now() + interval '5 minutes');
update "match" set time_end = now() + interval '30 minutes' where match_id = 3;
