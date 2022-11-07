-- roles_info
INSERT INTO roles_info (name)
VALUES ('ROLE_ADMIN');
INSERT INTO roles_info (name)
VALUES ('ROLE_OPERATOR');
INSERT INTO roles_info (name)
VALUES ('ROLE_COMPANY_ADMINISTRATOR');
INSERT INTO roles_info (name)
VALUES ('ROLE_COMPANY_ACCOUNTANT');
INSERT INTO roles_info (name)
VALUES ('ROLE_COMPANY_GENERAL');

-- ad_goal_info
INSERT INTO ad_goal_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('CONVERSION', 'N', 0, '2022-10-26 17:11:51', null, null);
INSERT INTO ad_goal_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('VISITING', 'N', 0, '2022-10-26 17:11:59', null, null);
INSERT INTO ad_goal_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('REACH', 'N', 0, '2022-10-26 17:12:14', null, null);
INSERT INTO ad_goal_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('VIEW', 'N', 0, '2022-10-28 11:26:17', null, null);

-- ad_type_info
INSERT INTO ad_type_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('DISPLAY', 'N', 0, '2022-10-26 17:11:06', null, null);
INSERT INTO ad_type_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('VIDEO', 'N', 0, '2022-10-26 17:11:26', null, null);

-- ad_type_goal_info
INSERT INTO ad_type_goal_info (ad_type_info_id, ad_goal_info_id, del_yn)
VALUES (1, 1, 'N');
INSERT INTO ad_type_goal_info (ad_type_info_id, ad_goal_info_id, del_yn)
VALUES (1, 2, 'N');
INSERT INTO ad_type_goal_info (ad_type_info_id, ad_goal_info_id, del_yn)
VALUES (2, 4, 'N');

-- media_info
INSERT INTO media_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('ANGKORCHAT', 'N', 0, '2022-10-26 18:08:45', null, null);

-- device_info
INSERT INTO device_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('PC', 'N', 0, '2022-10-26 18:07:06', null, null);
INSERT INTO device_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('ANDROID', 'N', 0, '2022-10-26 18:07:16', null, null);
INSERT INTO device_info (name, del_yn, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('IOS', 'N', 0, '2022-10-26 18:07:22', null, null);

-- user_info
INSERT INTO user_info (user_id, company_info_id, user_password, user_name, email, phone, active, first_pwd_yn,
                       pwd_wrong_cnt, pwd_upd_date, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('admin@test.com', null, '$2a$10$HADe0IGj92syHeV7z6NO0OK5Gsts.xpFCbI40cRVKj0KzRCSOpFuy',
        'admin', null, null, 'Y', 'N', 0, '2022-10-07 13:29:03', 0, '2022-10-07 13:29:03', 0, '2022-10-07 13:29:03');

INSERT INTO user_info (user_id, company_info_id, user_password, user_name, email, phone, active, first_pwd_yn,
                       pwd_wrong_cnt, pwd_upd_date, reg_user_no, reg_date, upd_user_no, upd_date)
VALUES ('advertiser@test.com', null, '$2a$10$HADe0IGj92syHeV7z6NO0OK5Gsts.xpFCbI40cRVKj0KzRCSOpFuy',
        'advertiser', null, null, 'Y', 'N', 0, '2022-10-07 13:29:03', 0, '2022-10-07 13:29:03', 0, '2022-10-07 13:29:03');

-- user_roles
INSERT INTO user_roles (user_no, roles_info_id)
VALUES (1, 1);

INSERT INTO user_roles (user_no, roles_info_id)
VALUES (2, 3);