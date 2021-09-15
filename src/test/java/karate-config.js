function fn() {
    var protocol = 'http';
    var hostname
        = karate.properties['zeppelin.server.hostname'] || 'zeppelin-server';
    var port = karate.properties['zeppelin.server.port'] || '80';

    if (karate.properties['zeppelin.server.https'] === 'true') {
        protocol = 'https';
        karate.configure('ssl', true);
    }

    var config = { demoBaseUrl: protocol + '://' + hostname + ':' + port }
    return config;
}