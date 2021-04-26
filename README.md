# Publishing-house-article-service
DDD business model example:

Background
There is a publishing house, which publishes the "Layer Weekly" magazine. The management of the publishing house all of a sudden decided to switch their employees to fully remote positions. In the process of establishing how the company would handle it, the management came up with a solution to exchange articles by e-mail. Unfortunately, the process was long and error-prone, so the Editor in Chief decided that they need a collaboration tool to ease the process of drafting and publishing articles. "IT to the rescue"! — our story begins here.

Implemented User Stories:
* As a journalist, I submit a draft article for publishing to a given topic
* As a copywriter, I suggest changes to the draft article I'm assigned to
* As a journalist, I can publish the article after all suggestions are resolved

# Important:
It's an DDD example, so focus of this project is the 'model' package.
The application have layered architecture.
Controllers layer is not implemented.
Data access layer is stubbed with simple hashmap so in memory data. After app restart that will disappear + 
its not stateless approach (scalability issue - client needs to reach same service instance each time).

# Tech stack:
* Java 11, Groovy
* Spring boot
* Spock tests

# Architectural Decisions:
1. Modelling entities / persistent data structures relationships
* Context: relationships between entites need to be implemented in the app model
* Decision: entities relationship are done trough objects and not by ids
* Consequences: the app model is coupled together and therefore is not ready to be distributed into separate services 
* Alternatives: model relationships by ids

2. How to achieve data consistency/integrity (accuracy, truth, up-to-date)
* Context: what kind of transactionality do we need  - ACID or BASE in our app. How to achieve it ?
* Decision: we need ACID transactionality for one aggregate root. One ACID transaction should span one and only one aggregate root - this way we can distribute our services into multiple services in the future and do no have to worry about distributed transactions.
* Consequences: the architecture of the data is open for distribution into separate and independetly deployable software components
* Alternatives: transactional boundaries spanning multiple aggregate roots

3. What app architecture do we need ?
* Context: what app arch do we need - layered or hexagonal or microkernel or pipes and filters ?
* Decision: layered (3) architecture is good enough for given use case. We do not need ports and adapters because of testability (mocking frameworks). Do not expect and replacements of any app layer in the future. Controller layer - web access point / web endpoint of the app. Take input data, validate it, pass to downstream (service) layer and prepare response if ready. Service layer - orchestrate calls to business logic and do some cross - orthogonal aspects that should be meet like transactionality. Business model layer - encapsulates domain representation model (data structures + behaviours). Data access layer  - integration with persistent storages or web services.
* Consequences: code is better organized and testable
* Alternatives: -

4. How to implement invariants checks
* Context: we need a way/pattern how to implement invariants checks
* Decision: conditional statements throwing exceptions should not be implemented in the constructors because if they change then loading data from persistent storage / other sevices can fail. Constructors should be hidden.
* Consequences: we can evolve invariants in the app model without worry about backward - compatiblity with already stored data 
* Alternatives: -

// not implemented
5. Events publication - 'at least once', 'at most once', 'exactly one semantics'
* Context: how to publish events in reliable way (simulating db and broker in 1 transaction without XA/2Phase Commit)
* Decision: publish events using 'outbox pattern' https://thorben-janssen.com/outbox-pattern-hibernate/ and deduplicate on consumer side which gives 'exactly once' semantics
* Consequences: events published respecting to db transactions
* Alternatives: different delivery guarantee policy

6. How to represent id fields in db structures ?
* Context: Surogate/synthetic key vs business/natural key ? App or db generated ? If surogate then UUID vs Sequence ?
* Decision: Natural keys are candidates to potential change for example (userId can be represented specific to given country / law jurisdiction that can change accoring to some political changes) which can cause db migration related problems. In production for perfromance constraints Ill choose sequence generated on the db level. For the sake of POC here and in-memory stub instead of db UUID is just enough choice.
* Consequences: We have id proof to changes + performance.
* Alternatives: other policy.

Not decided:
* What read/write pattern to use - single model vs CQRS vs Event Sourcing ?
* Do we need multitenancy ?
* System architecture (monolith, modular monolith, microservices, esb, ...) ?
* App framework - reactive/reactor pattern vs parallel workers ?
* Db engine/engines choice
* Cloud vs on prem vs multi vs hybrid + ci/cd + other infra e.g: ci/cd, message brokers, containers etc.
* Data replication, fail over, clustering, resiliency etc.
* Monitoring, containers orchestration, deployments strategy (in place, canary, blue green, ab, rollling) do we need service mesh ?
* And many, many more...

I know too less context / architecture drivers to take decision adressing any of above concerns.

# TODO:
* other things todo are marked with comments //todo in the code
* access to entities only trough aggregate root
* publish events with time, source, message etc
* code refactoring
* check DNA whats missing
