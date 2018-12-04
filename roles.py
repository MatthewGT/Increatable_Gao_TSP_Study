def insert_roles():
              roles = {
                  'User': (1|
                           2 |
                           3,True),
                  'Moderator': (2 |
                               3 |
                                6 |
                                9),
                  'Administrator': (0xff)
              }
              print(roles['User'][0])
# print(True|False)
insert_roles()

# for r in roles:
# role = Role.query.filter_by(name=r).first() if role is None:
#                       role = Role(name=r)
#                   role.permissions = roles[r][0]
#                   role.default = roles[r][1]
#                   db.session.add(role)
#               db.session.commit()