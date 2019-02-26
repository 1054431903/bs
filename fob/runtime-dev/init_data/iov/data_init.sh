#!/bin/bash
mongoimport -d iov -c base.area --file ./base.area.json
mongoimport -d iov -c sys.tenant --file ./sys.tenant.json
mongoimport -d iov -c tenant.app --file ./tenant.app.json
mongoimport -d iov -c tenant.staff --file ./tenant.staff.json
