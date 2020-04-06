db.createUser(
  {
    user: "superAdmin",
    pwd: "admin123",
    roles: [ { role: "root", db: "admin" } ]
  }
)

db.createUser(
  {
    user: "shopUser",
    pwd: "shop123",
    roles: [ { role: "readWrite", db: "shop" } ]
  }
)
