# Local Setup

To setup the firenoc in your local system, clone the [Core Service repository](https://github.com/egovernments/core-services).

## Dependencies

### Infra Dependency

- [x] Postgres DB
- [ ] Redis
- [ ] Elasticsearch
- [x] Kafka
  - [x] Consumer
  - [x] Producer

## Running Locally

To run the firenoc locally, you need to run the below command to port forward below services

```bash
 function kgpt(){kubectl get pods -n egov --selector=app=$1 --no-headers=true | head -n1 | awk '{print $1}'}

 kubectl port-forward -n egov $(kgpt idgen) 8087:8080 &
 kubectl port-forward -n egov $(kgpt user) 8088:8080 &
 kubectl port-forward -n egov $(kgpt workflow) 8089:8080 &
 kubectl port-forward -n egov $(kgpt location) 8090:8080 &
 kubectl port-forward -n egov $(kgpt firenoc-calculator) 8091:8080
 kubectl port-forward -n egov $(kgpt mdms) 8092:8080
``` 

Update below listed properties in `envVariables.js` before running the project:

```ini
EGOV_IDGEN_HOST: process.env.EGOV_IDGEN_HOST || "http://localhost:8087"
EGOV_USER_HOST: process.env.EGOV_USER_HOST || "http://localhost:8088"
EGOV_WORKFLOW_HOST: process.env.EGOV_WORKFLOW_HOST || "http://localhost:8089"
EGOV_LOCATION_HOST: process.env.EGOV_LOCATION_HOST || "http://localhost:8090"
#  If you are running firenoc calculator in your local system then mention server port of it
EGOV_FN_CALCULATOR_HOST: process.env.EGOV_FN_CALCULATOR_HOST || "http://localhost:8091"
EGOV_MDMS_HOST: process.env.EGOV_MDMS_HOST || "http://localhost:8092"
```

After updating the properties mentioned above, now start the fire-noc by running the command **npm run dev** in terminal.