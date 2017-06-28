#!/bin/bash

HOME_PATH=metamac-notices
TRANSFER_PATH=$HOME_PATH/tmp
DEPLOY_TARGET_PATH=/servers/metamac/tomcats/metamac01/webapps
ENVIRONMENT_RELATIVE_PATH_FILE=WEB-INF/classes/metamac/environment.xml
LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml
RESTART=1

if [ "$1" == "--no-restart" ]; then
    RESTART=0
fi


scp -r etc/deploy deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH
scp metamac-notifications-web/target/notices-internal-*.war deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/notices-internal.war
ssh deploy@estadisticas.arte-consultores.com <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh

    if [ $RESTART -eq 1 ]; then
        sudo service metamac01 stop
        checkPROC "metamac"
    fi


    ###
    # NAVIGATION-BAR-INTERNAL
    ###

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/notices-internal
    sudo mv $TRANSFER_PATH/notices-internal.war $DEPLOY_TARGET_PATH/notices-internal.war
    sudo unzip $DEPLOY_TARGET_PATH/notices-internal.war -d $DEPLOY_TARGET_PATH/notices-internal
    sudo rm -rf $DEPLOY_TARGET_PATH/notices-internal.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment.xml $DEPLOY_TARGET_PATH/notices-internal/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo cp $HOME_PATH/logback.xml $DEPLOY_TARGET_PATH/notices-internal/$LOGBACK_RELATIVE_PATH_FILE

    if [ $RESTART -eq 1 ]; then
        sudo chown -R metamac.metamac /servers/metamac
        sudo service metamac01 start
    fi

    #checkURL "http://estadisticas.arte-consultores.com/notices-internal" "metamac01"
    echo "Finished deploy"

EOF