# download and build
git clone https://github.com/jopereira/EscadaTPC-C
cd EscadaTPC-C
mvn package
cp target/tpc-c-0.1-SNAPSHOT-tpc-c.zip ..
cd ..
unzip tpc-c-0.1-SNAPSHOT-tpc-c.zip

# database properties
# etc/database-config.properties

# workload properties
# etc/workload-config.properties
# (set think time to false)

# create db
# docker exec -it postgres createdb -U postgres tpcc

sudo PGPASSWORD=postgres -u postgres createdb tpcc

cd tpc-c-0.1-SNAPSHOT

# load data
#cat etc/sql/postgresql/createtable.sql | docker exec -i postgres psql -U postgres -d tpcc
#cat etc/sql/postgresql/sequence.sql | docker exec -i postgres psql -U postgres -d tpcc

cat etc/sql/postgresql/createtable.sql | PGPASSWORD=postgres psql -U postgres -d tpcc
cat etc/sql/postgresql/sequence.sql | PGPASSWORD=postgres psql -U postgres -d tpcc

# database-config.properties MUDAR user e password
sed -i 's/alfranio/postgres/' etc/database-config.properties
sed -i -z 's/pass/postgres/2' etc/database-config.properties


./load.sh


# create functions and indexes
#cat etc/sql/postgresql/*01 | docker exec -i postgres psql -U postgres -d tpcc
#cat etc/sql/postgresql/createindex.sql | docker exec -i postgres psql -U postgres -d tpcc

cat etc/sql/postgresql/*01 | PGPASSWORD=postgres psql -U postgres -d tpcc
cat etc/sql/postgresql/createindex.sql | PGPASSWORD=postgres psql -U postgres -d tpcc

# run
./run.sh > /dev/null


# plot
wget https://gist.githubusercontent.com/jopereira/4086237/raw/78988818fa4f8ec37cb84382d3fe40d80856b285/showtpc.py

# single test plot
#python3 showtpc.py --bench=c -p <File.dat>

#python showtpc.py --bench=c -p *.dat


# single test plot (specific procedure)
#python3 showtpc.py --bench=c --tx=[payment,delivery,orderstatus,stocklevel,neworder] -p <FILE.dat>

#python showtpc.py --bench=c --tx=[payment,delivery,orderstatus,stocklevel,neworder] -p *.dat

# multi test plot (scalability curve)
#python3 showtpc.py --bench=c -p <MULTIPLE_DAT_FILES>
