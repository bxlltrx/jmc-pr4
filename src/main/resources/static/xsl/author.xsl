<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8" />

  <xsl:template match="/author">
    <html>
      <head><title>Author</title></head>
      <body>
        <h1>Author</h1>
        <ul>
          <li><b>ID:</b> <xsl:value-of select="id"/></li>
          <li><b>Name:</b> <xsl:value-of select="fullName"/></li>
          <li><b>Country:</b> <xsl:value-of select="country"/></li>
          <li><b>Birth date:</b> <xsl:value-of select="birthDate"/></li>
        </ul>
        <p>
          <a>
            <xsl:attribute name="href">/api/authors/<xsl:value-of select="id"/>/books</xsl:attribute>
            View books of this author (XML)
          </a>
        </p>
        <p><a href="/api/authors">Back to authors</a></p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
