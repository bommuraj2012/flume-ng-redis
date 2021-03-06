# Redis Extension for Flume NG

Redis extension for Flume NG ([http://flume.apache.org](http://flume.apache.org)). Tested with Apache Flume 1.4.0 and Redis 2.6.14.

[![Build Status](https://travis-ci.org/chiwanpark/flume-ng-redis.png?branch=master)](https://travis-ci.org/chiwanpark/flume-ng-redis) [![Coverage Status](https://coveralls.io/repos/chiwanpark/flume-ng-redis/badge.png)](https://coveralls.io/r/chiwanpark/flume-ng-redis)

## Current Version

* Development: 0.1-SNAPSHOT
* Stable: none

## Current Supported Features

* Custom sources using Redis [SUBSCRIBE](http://redis.io/commands/subscribe) command (single channel)

## Usage

1. Build this library witn ```mvn package``` command.
1. Copy ```flume-ng-redis-[VERSION].jar``` or ```flume-ng-redis-[VERSION]-jar-with-dependencies.jar``` into your flume library path.
1. Copy configuration sample file or create your own configuration.
1. Run Flume. (Following command is sample for RedisSubscribeDrivenSource.)

		bin/flume-ng agent -n agent -c conf -f conf/example-SubscribeDrivenSource.properties -Dflume.root.logger=DEBUG,console

## Dependencies

* Jedis 2.2.0 ([https://github.com/xetorthio/jedis](https://github.com/xetorthio/jedis))

## License

Copyright 2013 Chiwan Park

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.