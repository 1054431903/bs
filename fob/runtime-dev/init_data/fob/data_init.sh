#!/bin/bash
mongoimport -d web -c sys.authority --file ./sys.authority.json
mongoimport -d web -c tenant.role --file ./tenant.role.json
mongoimport -d web -c tenant.staff_roles --file ./tenant.staff_roles.json
