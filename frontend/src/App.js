import React, { Component } from 'react';
import axios from 'axios';
import ServiceInfoRow from './ServiceInfoRow';
import { Col, Grid, Row, Table } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';


class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      services: []
    }
  }

  componentDidMount() {
    setInterval(this.fetchData, 2000);
  }

  fetchData = () => {
    axios.get('/api/list')
      .then(({data}) => this.setState({services: data}))
      .catch(e => console.error(e));
  };

  render() {
    const rows = this.state.services.map(service =>
      <ServiceInfoRow key={service.name} service={service}/>
    );

    return (
      <Grid>
        <Row>
          <Col md={12}>
            <Table bordered>
              <thead>
              <th>Name</th>
              <th>Description</th>
              <th>Status</th>
              </thead>
              <tbody>
              {rows}
              </tbody>
            </Table>
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default App;
