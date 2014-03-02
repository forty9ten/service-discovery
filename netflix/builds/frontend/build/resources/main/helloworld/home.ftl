<#macro body>

<script type="text/javascript">
<#include "home.js"/>
</script>

<style>
    .explorer-container {
        margin: 10px;
        border: 1px solid #AAA;
        width: 8em;
        height: 6em;
        float: left;
        background: #ddd;
        cursor: pointer;
        display: table
    }

    .explorer-container p {
        display : table-cell;
        font-size: 1.2em;
        text-align: center;
        vertical-align: middle;
    }

    .explorer-container:hover {
        background: #CCC;
    }

    h1 {
        font-size: 1.2em;
        margin: 10px 10px 0px 10px;
    }

    .tipsy {
        font-size: 1em;
    }

</style>

<div id="internal-explorers">
  <h1>${data}</h1>
</div>

</#macro>