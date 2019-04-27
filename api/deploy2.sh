#!/bin/bash

cd ../app
npm run build
cd dist
aws s3 cp . s3://tutkit --recursive
