<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8" />

  <xsl:template match="/authors">
    <html>
      <head><title>Authors</title></head>
      <body>
        <h1>Authors</h1>
        <p>
          <a href="/api/authors">JSON</a> |
          <a href="/api/authors" />
        </p>
        <table border="1" cellpadding="6">
          <tr>
            <th>ID</th><th>Full name</th><th>Country</th><th>Birth date</th><th>Links</th>
          </tr>
          <xsl:for-each select="author">
            <tr>
              <td><xsl:value-of select="id"/></td>
              <td><xsl:value-of select="fullName"/></td>
              <td><xsl:value-of select="country"/></td>
              <td><xsl:value-of select="birthDate"/></td>
              <td>
                <a>
                  <xsl:attribute name="href">/api/authors/<xsl:value-of select="id"/></xsl:attribute>
                  view author (XML)
                </a>
                <xsl:text> | </xsl:text>
                <a>
                  <xsl:attribute name="href">/api/authors/<xsl:value-of select="id"/>/books</xsl:attribute>
                  books (XML)
                </a>
              </td>
            </tr>
          </xsl:for-each>
        </table>
        <p><a href="/api/books">Books (JSON)</a></p>
        <p><a href="/api/books" >Books (XML with XSL via Accept: application/xml)</a></p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
