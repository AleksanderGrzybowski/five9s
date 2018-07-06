import React, { Component } from 'react';
import axios from 'axios';
import ServiceInfoRow from './ServiceInfoRow';
import { Col, Grid, Row, Table } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';

const refreshInterval = 2000;
const endpoint = '/api/list';

class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      isReady: false,
      services: []
    }
  }

  componentDidMount() {
    setInterval(this.fetchData, refreshInterval);
    this.fetchData();
  }

  fetchData = () => {
    axios.get(endpoint)
      .then(({data}) => this.setState({isReady: true, services: data}))
      .catch(e => {
        this.setState({isReady: false});
        console.error(e);
      });
  };

  render() {
    const rows = this.state.services.map(service =>
      <ServiceInfoRow key={service.name} service={service}/>
    );

    const table = (
      <Table striped bordered hover style={{marginTop: 20}}>
        <thead>
        <tr>
          <th>Name</th>
          <th>Description</th>
          <th>Status</th>
          <th>Last output</th>
        </tr>
        </thead>
        <tbody>
        {rows}
        </tbody>
      </Table>
    );

    const loading = <h1 className="text-center" style={{fontSize: '20pt'}}>Please wait...</h1>;

    const view = this.state.isReady ? table : loading;

    return (
      <Grid fluid>
        <Row>
          <Col md={12}>
            {view}
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default App;
