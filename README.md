# CS530 SuccessCentral

## To work with MYSQL
1. Create a database in PHPMyAdmin (or your MySQL manager of choice). The name can be anything but it will be used by our app.
2. In the standalone.xml file (Wildfly Install > Standalone > Configuration > standalone.xml), paste in a new datasource:

    ```
    <datasource jndi-name="java:/SCDB" pool-name="SCDB">
        <connection-url>jdbc:mysql://localhost:3306/[YOUR DATABASE NAME]</connection-url>
        <driver>mysql</driver>
        <security>
            <user-name>root</user-name>
            <password>root</password>
        </security>
    </datasource>
    ```
        
    __NOTE:__ `jdni-name` and `pool-name` must match what's shown above. The connection URL and credentials should match your server and database.

3. Visit `localhost:8080/scapp/dev/dbsetup` in your browser and click 'Reset' to set up the mentee and mentor tables

    __NOTE:__ This will delete any contents in existing mentee and mentor tables
    
4. Visit `localhost:8080/scapp/menteeform` to test out the form. When you click 'Submit', the mentee table should be updated with the form's information.